import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SendProtocol {
    //private final InputStream in;
    //private final OutputStream out;
    private final Scanner inString;
    private final PrintWriter outString;
    private String recipient_name;
    private String sender_name;
    private static final String path = "C:\\Users\\Public\\Downloads\\";

    public SendProtocol (InputStream inputStream, OutputStream outputStream, String recipient_name) throws Exception {

        //this.in = inputStream;
        //this.out = outputStream;
        inString = new Scanner(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        outString = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
        this.recipient_name = recipient_name;

        startProtocol();

    }

    private void startProtocol () throws Exception{

        outString.println(recipient_name + " version:1.0");

        String[] strings = inString.nextLine().split(" ");

        if(!(strings[1]).equals("version:1.0")){
            throw new Exception("Error connect.");
        }

        sender_name = strings[0];

    }

    public String getMessage() throws Exception{
        String  type_message = inString.nextLine();

        if (type_message.equals("String message.")) return sender_name + ": " + inString.nextLine();

        if (type_message.equals("File message.")) {

            var filename = path + inString.nextLine();
            var new_file = new File(filename);
            var outputStream = new FileOutputStream(new_file);

            int lengthFile = Integer.parseInt(inString.nextLine());

            for(int i = 0; i < lengthFile; i++) {
                outputStream.write(Integer.parseInt(inString.nextLine()));
                outputStream.flush();
            }

            outputStream.close();

            return "System: get file " + filename;

        }

        throw new Exception("Not follow the protocol.");
    }

    public void sendMessage (String massage) {

        outString.println("String message.");
        outString.println(massage);

    }

    public void sendFile (File file) throws IOException {

        outString.println("File message.");
        outString.println(file.getName());

        var in = new FileInputStream(file);

        outString.println(in.available());

        while (in.available() > 0) {
            outString.println(in.read());
            outString.flush();
        }

        in.close();

    }

}
