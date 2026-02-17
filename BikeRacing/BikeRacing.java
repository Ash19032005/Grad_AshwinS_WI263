import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BikeRacing {

    private static final int NUMBER_OF_RACERS = 10;
    private static final int TOTAL_DISTANCE = 1000;
    private static final int CHECKPOINT_DISTANCE = 100;

    private static final Vector<RaceResult> raceLeaderboard = new Vector<>();
    private static final CyclicBarrier raceStartBarrier = new CyclicBarrier(NUMBER_OF_RACERS);
    private static final CountDownLatch raceFinishLatch = new CountDownLatch(NUMBER_OF_RACERS);

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(NUMBER_OF_RACERS);

        String[] bikeIds = {
                "B1", "B2", "B3", "B4", "B5",
                "B6", "B7", "B8", "B9", "B10"
        };

        System.out.println("--- The Race is about to start! ---");

        for (int i = 0; i < NUMBER_OF_RACERS; i++) {
            threadPool.execute(new RacerTask(bikeIds[i]));
        }

        try {
            raceFinishLatch.await();
            System.out.println("\n--- RACE FINISHED! LEADERBOARD ---");
            printLeaderboard();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            threadPool.shutdown();
        }
    }

    static class RacerTask implements Runnable {
        private final String racerName;
        private int currentDistance = 0;
        private int lastCheckpointReached = 0;

        public RacerTask(String racerName) {
            this.racerName = racerName;
        }

        @Override
        public void run() {
            try {
                raceStartBarrier.await();
                long raceStartTime = System.currentTimeMillis();

                while (currentDistance < TOTAL_DISTANCE) {
                    int stepDistance = (int) (Math.random() * 50) + 30;
                    currentDistance += stepDistance;

                    if (currentDistance > TOTAL_DISTANCE) {
                        currentDistance = TOTAL_DISTANCE;
                    }

                    printCheckpointProgress();
                    Thread.sleep((long) (Math.random() * 1000) + 500);
                }

                long totalTime = System.currentTimeMillis() - raceStartTime;
                System.out.println(racerName + " finished in " + (totalTime / 1000.0) + "s");

                raceLeaderboard.add(new RaceResult(racerName, totalTime));
                raceFinishLatch.countDown();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void printCheckpointProgress() {
            int checkpoint = (currentDistance / CHECKPOINT_DISTANCE) * CHECKPOINT_DISTANCE;

            if (checkpoint > lastCheckpointReached && checkpoint <= TOTAL_DISTANCE) {
                System.out.println(racerName + " has reached " + checkpoint + " meters.");
                lastCheckpointReached = checkpoint;
            }
        }
    }

    static class RaceResult {
        String racerName;
        long finishTime;

        RaceResult(String racerName, long finishTime) {
            this.racerName = racerName;
            this.finishTime = finishTime;
        }
    }

    private static void printLeaderboard() {
        System.out.printf("%-5s | %-10s | %-12s%n", "Pos", "Name", "Time (s)");
        System.out.println("---------------------------------------");

        for (int i = 0; i < raceLeaderboard.size(); i++) {
            RaceResult result = raceLeaderboard.get(i);
            System.out.printf("%-5d | %-10s | %-12.2f%n",
                    (i + 1),
                    result.racerName,
                    (result.finishTime / 1000.0));
        }
    }
}
