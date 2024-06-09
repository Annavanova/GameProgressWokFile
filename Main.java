package WorkInFileLesson.GameProgress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    private static String mainPaph = "C:\\Users\\juliya\\Desktop\\Games\\savegames\\";
    private static String savePaph1 = "C:\\Users\\juliya\\Desktop\\Games\\savegames\\saveGame1.dat";
    private static String savePaph2 = "C:\\Users\\juliya\\Desktop\\Games\\savegames\\saveGame2.dat";
    private static String savePaph3 = "C:\\Users\\juliya\\Desktop\\Games\\savegames\\saveGame3.dat";
    private static String zipPaph = "C:\\Users\\juliya\\Desktop\\Games\\savegames\\zip.zip";

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(6, 4, 2, 103.45);
        GameProgress gameProgress2 = new GameProgress(2, 5, 8, 15.5);
        GameProgress gameProgress3 = new GameProgress(3, 6, 10, 1563.4);
        saveGame(savePaph1, gameProgress1);
        saveGame(savePaph2, gameProgress2);
        saveGame(savePaph3, gameProgress3);
        //Создание списка объектов
        ArrayList<String> objectList = new ArrayList<>();
        objectList.add(savePaph1);
        objectList.add(savePaph2);
        objectList.add(savePaph3);
        //Архивирование
        zipFiles(zipPaph, objectList);
        //Удаление лишних файлов
        deleteFile(mainPaph);
    }

    static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream osobject = new ObjectOutputStream(fos)) {
            osobject.writeObject(gameProgress);
            System.out.println("Запись сериализованного объекта " + gameProgress + " выполнена!");
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    static void zipFiles(String paphZip, List<String> objectList) {
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(paphZip))) {
            for (String arr : objectList) {
                try (FileInputStream fis = new FileInputStream(arr)) {
                    ZipEntry zipEntry = new ZipEntry(arr);
                    zipOut.putNextEntry(zipEntry);
                    while (fis.available() > 0) {
                        zipOut.write(fis.read());
                    }
                    zipOut.closeEntry();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Архив готов");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void deleteFile(String mainPaph) {
        File[] listFailMain = new File(mainPaph).listFiles();
        for (File list : listFailMain) {
            if (!list.toString().contains("zip")) {
                list.delete();
                System.out.println("Файл " + list.getPath() + " удален");
            }
        }
    }
}
