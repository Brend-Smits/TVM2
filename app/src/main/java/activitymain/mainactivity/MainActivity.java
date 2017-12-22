package activitymain.mainactivity;

        import android.graphics.Color;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        //import android.telecom.Connection;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.ResultSet;
        import java.sql.SQLException;
       /* import com.mysql.fabric.xmlrpc.Client;
        import com.mysql.jdbc.MySQLConnection;
        import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
        import com.mysql.jdbc.jdbc2.optional.MysqlPooledConnection;*/

        import java.util.Arrays;
        import java.util.HashMap;
        import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button[] buttonArray = new Button[4];
    Button answerBtn1, answerBtn2, answerBtn3,answerBtn4;
    int idNumber, currentQuestion;
    int[] questionPositions;
    int[] buttonPositions;
    Connection connection;
    int currentScore;
    TextView questionTextView;

    HashMap<Integer, String[]> questionAnswerMap = new HashMap<Integer, String[]>();
    HashMap<Integer, String> answerMap = new HashMap<Integer, String>();
    HashMap<Integer, String> questionMap = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialise();
        populate();
        //print out question the sequence
        for (int item: questionPositions
             ) {
            System.out.println(item);
            
        }
 }
    public void initialise(){

        DBConnect connect = new DBConnect();
        ResultSet rsAnswers = connect.getData("SELECT * FROM antwoorden");
        //find the buttons
        questionTextView = (TextView) findViewById(R.id.QuestionTextView);
        answerBtn1 = (Button) findViewById(R.id.answerBtn1);
        answerBtn2 = (Button) findViewById(R.id.answerBtn2);
        answerBtn3 = (Button) findViewById(R.id.answerBtn3);
        answerBtn4 = (Button) findViewById(R.id.answerBtn4);

        //initialise the buttons
        buttonArray[0] = answerBtn1;
        buttonArray[1] = answerBtn2;
        buttonArray[2] = answerBtn3;
        buttonArray[3] = answerBtn4;

        //Map containing he question per ID
        questionMap.put(0, "Vraag 1");
        questionMap.put(1, "Vraag 2");
        questionMap.put(2, "Vraag 3");
        questionMap.put(3, "Vraag 4");
        questionMap.put(4, "Vraag 5");
        questionMap.put(5, "Vraag 6");

        //Map containing keys with all values in it
        questionAnswerMap.put(0, new String[] {"1.1", "1.2", "1.3", "1.4"} );
        questionAnswerMap.put(1, new String[] {"2.1", "2.2", "2.3", "2.4"} );
        questionAnswerMap.put(2, new String[] {"3.1", "3.2", "3.3", "3.4"} );
        questionAnswerMap.put(3, new String[] {"4.1", "4.2", "4.3", "4.4"} );
        questionAnswerMap.put(4, new String[] {"5.1", "5.2", "5.3", "5.4"} );
        questionAnswerMap.put(5, new String[] {"6.1", "6.2", "6.3", "6.4"} );
        //poep

        //stop alle data van de database in de hashmaps
        /*
        try{
            System.out.println("test");
            while(rsAnswers.next() == true) {
                System.out.println("test22");
                System.out.println(rsAnswers.getInt(1) - 1 + " " + rsAnswers.getString(2) + " " + rsAnswers.getString(3) + " " + rsAnswers.getString(4) + " " + rsAnswers.getString(5));
                questionAnswerMap.put(rsAnswers.getInt(1) - 1, new String[] {rsAnswers.getString(2), rsAnswers.getString(3),
                        rsAnswers.getString(4), rsAnswers.getString(5)} );
                String correctAntwoord = rsAnswers.getString(2);
            }}
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }*/

        //Map containing the correct answer
        answerMap.put(0, "1.1");
        answerMap.put(1, "2.2");
        answerMap.put(2, "3.3");
        answerMap.put(3, "4.2");
        answerMap.put(4, "5.3");
        answerMap.put(5, "6.2");

        currentQuestion = 0;
        questionPositions = getRandomPermutation(questionAnswerMap.size());

    }


    public void confirmQuestion(View view){
        String guessed = "";
        Button pressedButton = null;
        //check which button has been pressed
        if(view.getId() == R.id.answerBtn1){
            guessed = answerBtn1.getText().toString();
            pressedButton = answerBtn1;
        }
        else if(view.getId() == R.id.answerBtn2){
            guessed = answerBtn2.getText().toString();
            pressedButton = answerBtn2;

        }
        else if(view.getId() == R.id.answerBtn3){
            guessed = answerBtn3.getText().toString();
            pressedButton = answerBtn3;

        }
        else if(view.getId() == R.id.answerBtn4){
            guessed = answerBtn4.getText().toString();
            pressedButton = answerBtn4;
            answerBtn4.clearAnimation();

        }
        //if text equal to answer repopulate and put button colour to default
        if( guessed == answerMap.get(idNumber)){
            pressedButton.setBackgroundColor(Color.GREEN);
            try {
                Thread.sleep(500);
                pressedButton.setBackgroundColor(Color.LTGRAY);
            }catch(Exception ex){}
            pressedButton.clearAnimation();
            populate();
        }
    }

    public void populate(){
        //check if questionssequence is finished
        if(currentQuestion < questionPositions.length) {
            try {
                //assign the idnumber to get information from the hashmaps
                //id number is the equal of current number from the sequence
                System.out.println(currentQuestion);
                idNumber = questionPositions[currentQuestion];
                //set questiontext
                questionTextView.setText(questionMap.get(idNumber));

                //set all info of the buttons
                String[] answers = questionAnswerMap.get(idNumber);

                buttonPositions = getRandomPermutation(4);

                for (int i : buttonPositions) {
                    buttonArray[i].setText(questionAnswerMap.get(idNumber)[buttonPositions[i]]);
                }
                currentQuestion++;

            } catch (Exception ex) {
                //if exception print
                System.out.println("Exception");
            }
        }else{
            //stop when the sequence has ended
            System.out.println("Exit");
            System.exit(0);
        }
    }


    public static int[] getRandomPermutation (int length){

        // initialize array and fill it with {0,1,2...}
        int[] array = new int[length];
        for(int i = 0; i < array.length; i++)
            array[i] = i;

        for(int i = 0; i < length; i++){

            // randomly chosen position in array whose element
            // will be swapped with the element in position i
            // note that when i = 0, any position can chosen (0 thru length-1)
            // when i = 1, only positions 1 through length -1
            // NOTE: r is an instance of java.util.Random
            Random r = new Random();
            int ran = i + r.nextInt (length-i);

            // perform swap
            int temp = array[i];
            array[i] = array[ran];
            array[ran] = temp;
        }
        return array;
    }


}
