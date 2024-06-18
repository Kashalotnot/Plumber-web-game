package sk.tuke.gamestudio.game.plumber.CUI;

import sk.tuke.gamestudio.service.RatingService;

public class PlumberMenu {

        public static void printMenu(RatingService ratingService) {

            String resetColor = "\u001B[0m";
            String blueColor = "\u001B[34m";
            String line = "+-----------------------------------+";
           // System.out.println(line);
            System.out.print(blueColor);
            System.out.println(" ______   __         __  __     __    __     ______     ______     ______   \n" +
                    "/\\  == \\ /\\ \\       /\\ \\/\\ \\   /\\ \"-./  \\   /\\  == \\   /\\  ___\\   /\\  == \\  \n" +
                    "\\ \\  _-/ \\ \\ \\____  \\ \\ \\_\\ \\  \\ \\ \\-./\\ \\  \\ \\  __<   \\ \\  __\\   \\ \\  __<  \n" +
                    " \\ \\_\\    \\ \\_____\\  \\ \\_____\\  \\ \\_\\ \\ \\_\\  \\ \\_____\\  \\ \\_____\\  \\ \\_\\ \\_\\\n" +
                    "  \\/_/     \\/_____/   \\/_____/   \\/_/  \\/_/   \\/_____/   \\/_____/   \\/_/ /_/\n" +
                    "                                                                            \n" +
                    " ______     ______     __    __     ______                                  \n" +
                    "/\\  ___\\   /\\  __ \\   /\\ \"-./  \\   /\\  ___\\                                 \n" +
                    "\\ \\ \\__ \\  \\ \\  __ \\  \\ \\ \\-./\\ \\  \\ \\  __\\                                 \n" +
                    " \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\_\\ \\ \\_\\  \\ \\_____\\                               \n" +
                    "  \\/_____/   \\/_/\\/_/   \\/_/  \\/_/   \\/_____/                               ");
            System.out.print(resetColor);
            System.out.println(line);
            System.out.print("|        Average Rating:      ");
            for(int i = 0; i < 5; i++){
                int averageRating = ratingService.getAverageRating("Plumber");
                System.out.print(i < averageRating ? "*" : " ");
            }
            System.out.println(" |");
            System.out.println(line);
            System.out.println("|                                   |");
            System.out.println("|        1. Start New Game          |");
            System.out.println("|        2. Rules                   |");
            System.out.println("|        3. View High Scores        |");
            System.out.println("|        4. Exit                    |");
            System.out.println("|                                   |");
            System.out.println(line);
            System.out.println("|     Use the numbers to select     |");
            System.out.println(line);
        }
}
