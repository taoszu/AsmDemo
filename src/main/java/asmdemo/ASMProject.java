package asmdemo;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import asmdemo.method.MethodVisitorHub;

public class ASMProject {

  static String ROOT_SUFFIX = "your path";

  public static void main(String[] args) {
    try {
      String classPath = "asmdemo/ModifyInstanceClass";

      ClassReader classReader = new ClassReader(classPath);
      ClassWriter classWriter = new ClassWriter(classReader, 0);
      ClassVisitor classVisitor = new ClassVisitorDemo(classWriter);

      classReader.accept(classVisitor, 0);


      File file = new File(ROOT_SUFFIX + "ClassDynamicLoader/AsmDemo/build/classes/java/main/asmdemo/ModifyInstanceClass.class");
      FileOutputStream output = new FileOutputStream(file);
      output.write(classWriter.toByteArray());
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    ModifyInstanceClass modifyClass = new ModifyInstanceClass();
    modifyClass.print();
    modifyClass.connectStr();
  }

  private static class ClassVisitorDemo extends ClassVisitor {
    ClassVisitorDemo(ClassVisitor classVisitor) {
      super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visitEnd() {

      cv.visitField(Opcodes.ACC_PRIVATE, "timer", Type.getDescriptor(long.class), null, null);

      MethodVisitor methodVisitor = cv.visitMethod(Opcodes.ACC_PUBLIC, "newFunc","()V", null,null);
      methodVisitor.visitInsn(Opcodes.RETURN);
      methodVisitor.visitMaxs(0,1);

      super.visitEnd();
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
      MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);

      if (name.equals("print") && desc.equals("()V")) {
        methodVisitor = new MethodVisitorHub.FirstMethodVisitor(methodVisitor);
      } else if(name.equals("print") && desc.equals("(Ljava/lang/String;)V")) {
        methodVisitor = new MethodVisitorHub.SecondMethodVisitor(methodVisitor);
      } else if (name.equals("connectStr")) {
        methodVisitor = new MethodVisitorHub.ThirdMethodVisitor(methodVisitor);
      }
      return methodVisitor;
    }


  }



}
