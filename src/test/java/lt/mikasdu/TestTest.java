package lt.mikasdu;

import org.junit.Test;

import java.io.File;

public class TestTest {
@Test
 public void mkdir(){
    File folder = new File("C://dir/dir/dir/dir");
    if (!folder.exists()) {
        System.out.println("making dirs");
        folder.mkdirs();
    }
 }


}
