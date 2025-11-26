package onlinestore;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class RegisteredUsers {
    private static final HashMap<Integer, String> registeredUsers = new HashMap<>();

    static {
        registeredUsers.put(MurmurHash2.hash32("admin1"), "1234");
        registeredUsers.put(MurmurHash2.hash32("admin2"), "5678");
    }

    private RegisteredUsers() {}

    public static boolean signUp(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        String realPassword = registeredUsers.getOrDefault(MurmurHash2.hash32(username), null);
        if (password.equals(realPassword)) {
            return true;
        }

        return false;
    }

    private static final class MurmurHash2 {
        private static final int M = 0x5bd1e995;
        private static final int R = 24;
        private static final int SEED = 0x9747b28c;

        public static int hash32(String s) {
            byte[] data = s.getBytes(StandardCharsets.UTF_8);
            int len = data.length;
            int h = SEED ^ len;
            int i = 0;

            while (len >= 4) {
                int k = (data[i] & 0xFF)
                        | ((data[i + 1] & 0xFF) << 8)
                        | ((data[i + 2] & 0xFF) << 16)
                        | ((data[i + 3] & 0xFF) << 24);
                k *= M;
                k ^= (k >>> R);
                k *= M;

                h *= M;
                h ^= k;

                i += 4;
                len -= 4;
            }

            switch (len) {
                case 3 -> { h ^= (data[i + 2] & 0xFF) << 16; h ^= (data[i + 1] & 0xFF) << 8; h ^= (data[i] & 0xFF); h *= M; }
                case 2 -> { h ^= (data[i + 1] & 0xFF) << 8; h ^= (data[i] & 0xFF); h *= M; }
                case 1 -> { h ^= (data[i] & 0xFF); h *= M; }
            }

            h ^= (h >>> 13);
            h *= M;
            h ^= (h >>> 15);
            return h;
        }
    }
}
