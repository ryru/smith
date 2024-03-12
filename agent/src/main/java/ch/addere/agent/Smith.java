package ch.addere.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.instrument.Instrumentation;

import static java.lang.String.format;

public class Smith {

  private static final Logger LOGGER = LoggerFactory.getLogger(Smith.class);

  /**
   * Static loading of agent.
   */
  public static void premain(String agentArgs, Instrumentation inst) {
    String className = "ch.addere.world.WorldImpl";
    transformClass(className, inst);
  }

  private static void transformClass(String className, Instrumentation instrumentation) {
    Class<?> targetCls = null;
    ClassLoader targetClassLoader = null;

    try {
      targetCls = Class.forName(className);
      targetClassLoader = targetCls.getClassLoader();
      transform(targetCls, targetClassLoader, instrumentation);
      return;
    } catch (Exception e) {
      LOGGER.error("Class [{}] not found with Class.forName", className);
    }

    for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
      if (clazz.getName().equals(className)) {
        targetCls = clazz;
        targetClassLoader = clazz.getClassLoader();
        transform(targetCls, targetClassLoader, instrumentation);
        return;
      }
    }
    throw new RuntimeException(format("Failed to find class [%s]", className));
  }

  private static void transform(Class<?> clazz, ClassLoader classLoader, Instrumentation instrumentation) {
    WorldTransformer wt = new WorldTransformer(clazz.getName(), classLoader);
    instrumentation.addTransformer(wt, true);
    try {
      instrumentation.retransformClasses(clazz);
    } catch (Exception e) {
      throw new RuntimeException(format("Transform failed for [%s]", clazz.getName()), e);
    }
  }
}
