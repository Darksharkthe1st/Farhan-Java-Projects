import java.awt.Toolkit;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Graphy3Driver {
    private static JFrame mainframe;
    private static int delay;
    private static int width, height;
    private static int wait;
    static double fov = 90;
	static double zFar = 100;
	static double zNear = 4;

    public static void main(String[] args) throws Exception {
        mainframe = new JFrame();
        delay = 20;

        width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 50;

        System.out.println("begin");
        mainframe.setSize(1920, 800);
        mainframe.setResizable(false);
        
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // RadixDriver.width = requestInt("What width is your screen (in pixels)?", 800, 3840);
        // RadixDriver.height = requestInt("What height is your screen? (in pixels)", 800, 1280);

        //We run a thread for the Graphics.
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    mainframe.repaint();
                }
            }
        }.start();

        wait = 0;

        //Code w/ thread for user action
        do {
            mainCode();
            System.out.println("BRO");
        } while (wait == 1);

        System.out.println("DONE");
        System.exit(0);
    }

    public static void mainCode() throws InterruptedException {
        mainframe.setVisible(false);        
        mainframe.setSize(width, height);

        Renderer paney = new Renderer();
        mainframe.add(paney);

        mainframe.setVisible(true);

        //0 represents keep going
        wait = 0;

        //We run one thread for the sort
        new Thread() {
            public void run() {
                while (true) {
                    paney.perform();
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        while (wait == 0) {
            Thread.sleep(10);
        }

        //Remove the finished graphics
        //mainframe.remove(paney);
    }


    @Deprecated
    //Deprecated b/c needs to be fixed to input .obj files
    public static File inputObject() {
        File f = null;

        // while (true) {
        //     String filepath = JOptionPane.showInputDialog(mainframe, "Give the path of a .CSV file");
        //     f = new File(filepath);

        //     //If statements to verify file is usable
        //     if (!f.canRead()){
        //         JOptionPane.showMessageDialog(mainframe, "Invalid file. Try again.","ERROR!",JOptionPane.ERROR_MESSAGE);
        //     } else if (!isCSV(f.getName())) {
        //         JOptionPane.showMessageDialog(mainframe, "File must be a CSV.","ERROR!",JOptionPane.ERROR_MESSAGE);
        //     } else if (!isValid(f)) {
        //         JOptionPane.showMessageDialog(mainframe, "CSV Contents must be integers only.","ERROR!",JOptionPane.ERROR_MESSAGE);
        //     } else {
        //         break;
        //     }
        // }
        return f;
    }

    
        //Set up a slider
        //Use when needed
        //Deprecated b/c has to be adjusted to fit current project
        @Deprecated
    public static void setupSlider() {
        // JSlider slidey = new JSlider(JSlider.HORIZONTAL, 0, 20, 10);
        // slidey.setMajorTickSpacing(5);
        // slidey.setMinorTickSpacing(1);
        // slidey.setSize(width, 70);
        // slidey.setPaintTicks(true);
        // slidey.setPaintLabels(true);

        // //Event for when the slider is moved
        // slidey.addChangeListener(new ChangeListener() {

        //     @Override
        //     public void stateChanged(ChangeEvent e) {
        //         /*Value */ = slidey.getValue();
        //     }
            
        // });
    }

    /**
     * @param title Title of Radio Window
     * @param message Message/Question to be displayed above options
     * @param options Labels of each option as Strings
     * @return
     */
    public static int radioWindow(String title, String message, String[] options) {
        return JOptionPane.showOptionDialog(mainframe, message, title, 
        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
    }

    /** Starts in the project directory and hunts recursively for the file requested
     * @param filename Name of file to be hunted for
     * @return File object representing the file or null if not present
     */
    public static File fileHunt(String filename) {
        //Gets the project directory
        File dir = new File(new File("").getAbsolutePath() + "\\");

        //Searches in that directory
        return recurFileHunt(dir.getAbsolutePath(), filename);
    }

    // Recursively goes through folders to find the file
    /** Finds the file by recursively searching through subdirectories, each branch representing one directory until all subdirectories exhausted or file is found
     * @param path The current directory being hunted through
     * @param filename Name of file to be hunted for
     * @return File object representing the file or null if not present
     */
    private static File recurFileHunt(String path, String filename) {
        // Get the list of files in the directory
        File[] files = new File(path).listFiles();

        // Check if it's there
        for (File f : files) {
            // Return if found
            if (!f.isDirectory() && f.getName().equals(filename))
                return f;
        }

        // Go through the directories, recursively search them
        for (File f : files) {
            // Only go through a directory if actually a directory
            if (f.isDirectory()) {
                // Hunt recursively
                File result = recurFileHunt(f.getAbsolutePath() + "\\", filename);
                if (result != null)
                    return result; // The only way it's not null is if we found it!
            }
        }

        //If file never found
        return null;
    }

    // Asks user for an integer value with message, int range inclusive, inclusive
    //Uses 
    /**
     * Makes use of javax.swing's JOptionPane to input an integer from the user.
     * Type checking built in.
     * @param message The request to be displayed to the user
     * @param min Minimum value for the user to input (inclusive); 
     * If min == null, assumed to be Integer.MIN_VALUE
     * @param max Maximum value for the user to input (inclusive); 
     * If max == null, assumed to be Integer.MAX_VALUE
     * @return The value the user inputs
     */
    public static int requestInt(String message, Integer min, Integer max) {
        

        //Dummy variable to represent the user's input as a string
        String answer = "";
        //Dummy variable to represent the user's input as an int
        int output;

        if (min == null) //If min == null, assumed to be Integer.MIN_VALUE
            min = Integer.MIN_VALUE;
        if (max == null) //If max == null, assumed to be Integer.MAX_VALUE
            max = Integer.MAX_VALUE;
        
        //Repeat until good input retrieved
        while (true) {

            //Get user input
            answer = JOptionPane.showInputDialog(mainframe, message);
            //Check if it's a proper int
            try {
                //If this throws an error, then we know the integer wasn't proper
                output = Integer.valueOf(answer);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(mainframe, "You must input an integer.","ERROR!",JOptionPane.ERROR_MESSAGE);
                continue;
            }

            //Check if the integer's in the proper range
            if (output >= min && output <= max) {
                break;
            } else { //Tell the user how they should change their input to make it work
                if (output < min)
                    JOptionPane.showMessageDialog(mainframe, "Your number is way too small! Make sure it's >= to " + min,"ERROR!",JOptionPane.ERROR_MESSAGE);
                else
                    JOptionPane.showMessageDialog(mainframe, "Your number is way too big! Make sure it's <= to " + max,"ERROR!",JOptionPane.ERROR_MESSAGE);
            }
        }
        return output;
    }

    public static int getWidth() {
        return mainframe.getWidth();
    }

    public static int getHeight() {
        return mainframe.getHeight();
    }

}


