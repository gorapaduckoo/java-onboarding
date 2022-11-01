package onboarding;

import java.util.*;

public class Problem7 {
    /*
    1. 친구 관계를 전부 받은 후, 친구 목록 갱신
    2. 공통 친구 수*10만큼 점수 갱신
    3. 방문자 점수 갱신
    3. 점수-이름 순 정렬
     */

    static HashMap<String, List<String>> friendList = new HashMap<>();
    static HashMap<String, Integer> scoreBoard = new HashMap<>();

    public static List<String> solution(String user, List<List<String>> friends, List<String> visitors) {
        List<String> answer = new ArrayList<>();
        for (int i=0; i<friends.size(); i++){
            String user1 = friends.get(i).get(0);
            String user2 = friends.get(i).get(1);
            addFriend(user1, user2);
            addFriend(user2, user1);
        }

        List<String> userList = new ArrayList<>(friendList.keySet());
        for (int i=0; i<userList.size(); i++){
            if (user.equals(userList.get(i))) continue;
            int commonFriend = countCommonFriend(user, userList.get(i));
            addScore(userList.get(i), commonFriend*10);
        }

        for (int i=0; i<visitors.size(); i++){
            addScore(visitors.get(i), 1);
        }

        List<String> finalScoreBoard = sortScoreBoard();
        for (int i=0; i<3; i++){
            answer.add(finalScoreBoard.get(i));
        }
        return answer;
    }

    // user1의 친구로 user2를 추가하는 함수 addFriend()
    private static void addFriend(String user1, String user2) {
        if(!friendList.containsKey(user1)){
            friendList.put(user1, new ArrayList<>());
        }
        List<String> user1FriendList = friendList.get(user1);
        user1FriendList.add(user2);
        friendList.put(user1, user1FriendList);
    }

    // user1과 user2 사이의 공통 친구 수를 알아내는 함수 countCommonFriend()
    private static int countCommonFriend(String user1, String user2) {
        int cnt = 0;
        List<String> user1FriendList = friendList.get(user1);
        List<String> user2FriendList = friendList.get(user2);

        for (int i=0; i<user1FriendList.size(); i++){
            String user1Friend = user1FriendList.get(i);
            if(user2FriendList.contains(user1Friend)) {
                cnt++;
            }
        }
        return cnt;
    }

    // A에게 점수를 n만큼 추가해주는 함수
    private static void addScore(String user, int score){
        if(!scoreBoard.containsKey(user)){
            scoreBoard.put(user, 0);
        }
        int userScore = scoreBoard.get(user);
        userScore += score;
        scoreBoard.put(user, userScore);
    }
    // 사용자를 점수-이름 순으로 정렬해주는 함수 sortScoreBoard()
    private static List<String> sortScoreBoard() {
        List<String> users = new ArrayList<>(scoreBoard.keySet());

        users.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if(scoreBoard.get(o2).compareTo(scoreBoard.get(o1))==0) {
                    return o1.compareTo(o2);
                } else {
                    return scoreBoard.get(o2).compareTo(scoreBoard.get(o1));
                }
            }
        });

        return users;
    }
}
