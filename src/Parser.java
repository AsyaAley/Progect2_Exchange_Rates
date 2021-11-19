import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Parser {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.samru.ru/bisnes/kurs_valuty_samara/");
            URLConnection urlConnection = url.openConnection(); //создаем соединение с сайтом
            HttpsURLConnection connection = (HttpsURLConnection) urlConnection; // уточняем тип соединения

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "cp1251")); //создаем буфер в который передаем ридер, читающий входящий поток нашего соединения, уточняем кодировку
            String inf =""; // переменная для хранения прочтенного
            String str; // для построчного чтения
            while ((str = in.readLine()) != null) { //пока есть строки во входящем потоке
                if (str.indexOf("<table  width=\'600\' border=\'0\' cellpadding=\'0\' cellspacing=\'0\'>") != -1) {
                    inf += str + "\n";// выбираем строку с нужным нам куском кода и записываем ее index0f возвращает с какого символа начинается этот кусок в строке, если строк с таким кодом нет, то возвращает -1
                }
            }
            String [] banks_info = inf.split("target='_blank'>"); // после того, как нашли кусок кода, который удобно делить нашу строку на элементы, создаем массив из этих элементов, дальше работаем с каждым отдельным
            for (int i = 1; i < banks_info.length ; i++) { //начинаем со второго элемента, тк нужные нам данные начинаются там
                String [] rates = banks_info [i].split("</td><td align='center'>");// делим каждый полученный на предыдущем этапе элемент, получаем массив из названия банка и цифр
                int end = rates [0].indexOf("</A>"); // в первом элементе массива будет название банка и кусок кода
                String bank = rates[0].substring(0,end); // отсекаем ненужные нам эелементы, чтобы получить чистое название банка
                String line = bank+ ' ' + rates[1] + ' ' + rates[2] + ' ' + rates[3]+ ' ' + rates[4]; // генерируем строку для печати
                System.out.println( line );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
