import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.time.DayOfWeek;

//Block containing details of each part of a schedule
public class ScheduleBlock {
    private String description;
    private String title;
    private int courseID;
    private String subject;
    private String section;
    private Timing[] timings;

    public ScheduleBlock(Timing[] timings) {
        this.timings = timings;
    }

    public void paintMe(Graphics g) {
        for (Timing t : timings) {
            g.setColor(Color.red);
            Rectangle2D s = t.getScaledBlock();
            g.fillRoundRect((int)s.getX(), (int)s.getY(), (int)s.getWidth(), (int)s.getHeight(), 10, 10);
        }
    }

    
}
