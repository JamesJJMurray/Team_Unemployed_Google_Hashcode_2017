import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        String[] params = reader.readLine().split(" ");

        int videos = Integer.valueOf(params[0]);
        int endpoints = Integer.valueOf(params[1]);
        int requests = Integer.valueOf(params[2]);
        int caches = Integer.valueOf(params[3]);
        int cacheSizes = Integer.valueOf(params[4]);

        ArrayList<CacheStorage> instances = new ArrayList<CacheStorage>();

        params = reader.readLine().split(" ");
        int[] videoSizes = new int[videos];
        for (int k = 0; k < videos; k++)
            videoSizes[k] = Integer.valueOf(params[k]);

        int[] datacenterLatencies = new int[endpoints];
        int[][] endpointCaches = new int[endpoints][caches];

        for (int k = 0; k < endpoints; k++)
        {
            params = reader.readLine().split(" ");
            datacenterLatencies[k] = Integer.valueOf(params[0]);

            int n = Integer.valueOf(params[1]);
            for (int j = 0; j < n; j++)
            {
                params = reader.readLine().split(" ");
                int cache = Integer.valueOf(params[0]);
                int latency = Integer.valueOf(params[1]);
                endpointCaches[k][cache] = latency;
            }
        }

        int[][] videoRequests = new int[videos][endpoints];
        for (int k = 0; k < requests; k++)
        {
            params = reader.readLine().split(" ");
            int video = Integer.valueOf(params[0]);
            int endpoint = Integer.valueOf(params[1]);
            int req  = Integer.valueOf(params[2]);

            videoRequests[video][endpoint] = req;
        }

        for (int k = 0; k < endpoints; k++)
        {
            System.out.println(k);
            for (int j = 0; j < videos; j++)
            {
                if (videoRequests[j][k] != 0)
                {
                    for (int i = 0; i < caches; i++)
                    {
                        if (endpointCaches[k][i] != 0)
                        {
                           createStorage(instances, j, k, i, videoRequests[j][k], videoSizes[j], endpointCaches[k][i]);
                        }
                    }
                }
            }
        }

        System.out.println(instances);

//        System.out.println(Arrays.toString(videoSizes));
//        System.out.println(Arrays.toString(datacenterLatencies));
//
//        for (int k = 0; k < endpointCaches.length; k++)
//            System.out.println(Arrays.toString(endpointCaches[k]));
//
//        for (int k = 0; k < videoRequests.length; k++)
//            System.out.println(Arrays.toString(videoRequests[k]));

//        char[][] board = new char[r][c];
//
//        for (int k = 0; k < r; k++) {
//            String line = reader.readLine();
//            for (int j = 0; j < c; j++)
//                board[k][j] = line.charAt(j);
//        }

//        for (int k = 0; k < r; k++)
//            System.out.println(Arrays.toString(board[k]));
    }

    static class CacheStorage {
        int video;
        HashMap<Integer, Double> endpoints;
        int cacheid;
        int videoSize;
        double totalPriority;
        ArrayList<Integer> caches;

        CacheStorage()
        {
            caches = new ArrayList<Integer>();
            endpoints = new HashMap<Integer, Double>();
        }

        void addPriority(int endpoint, double priority)
        {
            endpoints.put(endpoint, priority);
            totalPriority += priority;
        }

        @Override
        public String toString() {
            return "CacheStorage{" +
                    "video=" + video +
                    ", endpoints=" + endpoints.toString() +
                    ", cacheid=" + cacheid +
                    ", videoSize=" + videoSize +
                    ", totalPriority=" + totalPriority +
                    ", caches=" + caches.toString() +
                    "}\n";
        }
    }

    static void createStorage(ArrayList<CacheStorage> a, int video, int endpoint, int cache, int req, int size, int latency)
    {
//        System.out.println("create storage...");
        for (int k = 0; k < a.size(); k++)
        {
            CacheStorage curr = a.get(k);
            if (curr.video == video && curr.cacheid == cache)
            {
                curr.addPriority(endpoint, getPriority(req, size, latency));
                curr.caches.add(cache);
                return;
            }
        }

        CacheStorage c = new CacheStorage();
        c.video = video;
        c.videoSize = size;
        c.addPriority(endpoint, getPriority(req, size, latency));
        c.caches.add(cache);
        a.add(c);
    }

    static class Endpoint
    {
        int dataCenterLatency;
        ArrayList<Integer> caches = new ArrayList<Integer>();
        ArrayList<Integer> latencies = new ArrayList<Integer>();
        ArrayList<Integer> videos = new ArrayList<Integer>();
        ArrayList<Integer> requests = new ArrayList<Integer>();
    }

    static double getPriority(int req, int size, int latency)
    {
        return (double)req/(double)(size*latency);
    }
}
