package asmdemo.method;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodVisitorHub {


  public static class FirstMethodVisitor extends MethodVisitor {
    public FirstMethodVisitor(MethodVisitor mv) {
      super(Opcodes.ASM5, mv);
    }

    /**
     * 进入方法 插入System.out.print("hello world")这行代码
     */
    @Override
    public void visitCode() {
      super.visitCode();
      mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
      mv.visitLdcInsn("hello world");
      mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
      mv.visitMaxs(2,1);
    }
  }

  public static class SecondMethodVisitor extends MethodVisitor {
    public SecondMethodVisitor(MethodVisitor mv) {
      super(Opcodes.ASM5, mv);
    }

    /**
     * 进入方法 取出方法参数字符串s 插入System.out.print(s)这行代码
     */
    @Override
    public void visitCode() {
      super.visitCode();
      mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
      mv.visitVarInsn(Opcodes.ALOAD, 1);
      mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
      super.visitMaxs(maxStack, maxLocals);
      mv.visitMaxs(2, 2);
    }
  }


  public static class ThirdMethodVisitor extends MethodVisitor {
    public ThirdMethodVisitor(MethodVisitor mv) {
      super(Opcodes.ASM5, mv);
    }

    /**
     * 进入方法
     */
    @Override
    public void visitCode() {
      super.visitCode();
      mv.visitVarInsn(Opcodes.ALOAD, 0);
      mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
      mv.visitInsn(Opcodes.LNEG);
      mv.visitFieldInsn(Opcodes.PUTFIELD, "asmdemo/ModifyInstanceClass", "timer", "J");
    }

    @Override
    public void visitInsn(int opcode) {

      if ( opcode == Opcodes.RETURN) {
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitInsn(Opcodes.DUP);

        mv.visitFieldInsn(Opcodes.GETFIELD, "asmdemo/ModifyInstanceClass", "timer", "J");
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        mv.visitInsn(Opcodes.LADD);
        mv.visitFieldInsn(Opcodes.PUTFIELD, "asmdemo/ModifyInstanceClass", "timer", "J");

        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitFieldInsn(Opcodes.GETFIELD, "asmdemo/ModifyInstanceClass", "timer", "J");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V", false);
      }
      super.visitInsn(opcode);
    }


    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        mv.visitMaxs(5, 3);

    }
  }


}
