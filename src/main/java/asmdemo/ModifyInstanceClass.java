package asmdemo;

public class ModifyInstanceClass {
  public ModifyInstanceClass() {

  }

  public void print() {
  }

  public void print(String s) {
  }

  public void printTime() {
   //long time =  - System.currentTimeMillis();
    //time += System.currentTimeMillis();
    //System.out.print(time);
  }


  public void connectStr() {
    String s = "";
    for (int i = 0; i < 10000; i ++) {
      s += i;
    }
  }



}
