import java.io.PrintWriter;
import java.util.ListIterator;

/**
 * Some simple experiments with SimpleDLLs
 * @author Sam R. (taken from linked-list lab)
 */
public class SDLLExpt {
  public static void main(String[] args) throws Exception {
    PrintWriter pen = new PrintWriter(System.out, true);

    //SimpleList <Integer> test = new SimpleCDLL<Integer>();
    //ListIterator <Integer> i1 = test.listIterator();
    //ListIterator <Integer> i2 = test.listIterator();
    //i1.add(2);
    //i1.add(1);
  
    SimpleListExpt.expt1(pen, new SimpleCDLL<String>());
    SimpleListExpt.expt2(pen, new SimpleCDLL<String>());
    SimpleListExpt.expt3(pen, new SimpleCDLL<String>());
    SimpleListExpt.expt4(pen, new SimpleCDLL<String>(), 3);
  } // main(String[]
} // SDLLExpt
