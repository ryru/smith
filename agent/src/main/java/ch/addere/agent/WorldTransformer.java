package ch.addere.agent;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class WorldTransformer implements ClassFileTransformer {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorldTransformer.class);

  private final String targetClassName;
  private final ClassLoader targetClassLoader;

  public WorldTransformer(String targetClassName, ClassLoader targetClassLoader) {
    this.targetClassName = targetClassName;
    this.targetClassLoader = targetClassLoader;
  }

  @Override
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
      ProtectionDomain protectionDomain, byte[] classfileBuffer) {
    byte[] byteCode = classfileBuffer;
    String finalTargetClassName = this.targetClassName.replaceAll("\\.", "/");

    if (!className.equals(finalTargetClassName)) {
      return byteCode;
    }

    if (className.equals(finalTargetClassName) && loader.equals(targetClassLoader)) {
      LOGGER.info("[Agent] Transforming class WorldImpl");
      try {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get(targetClassName);
        CtMethod m = cc.getDeclaredMethod("initialise");
        m.addLocalVariable("startTime", CtClass.longType);
        m.insertBefore("startTime = System.currentTimeMillis();");

        StringBuilder endBlock = new StringBuilder();

        m.addLocalVariable("endTime", CtClass.longType);
        m.addLocalVariable("opTime", CtClass.longType);
        endBlock.append("endTime = System.currentTimeMillis();");
        endBlock.append("opTime = (endTime - startTime) / 1000;");

        endBlock.append("LOGGER.info(\"[Application] Initialisation operation completed in: "
            + "\" + opTime + \" seconds!\");");

        m.insertAfter(endBlock.toString());

        byteCode = cc.toBytecode();
        cc.detach();
      } catch (NotFoundException | CannotCompileException | IOException e) {
        LOGGER.error("Exception", e);
      }
    }
    return byteCode;
  }
}
