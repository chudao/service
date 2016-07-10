(ns chudao.cache.user)

(def user-cache (atom {}))

(defn put-user-in-cache
  [session user-data]
  (swap! user-cache assoc session user-data))

(defn user-already-authenticated?
  [session]
  (contains? @user-cache session))

(defn get-user-id
  [session]
  (if (user-already-authenticated? session)
    (:user-id (get @user-cache session))))

(defn user-stylist?
  [session]
  (if (user-already-authenticated? session)
    (= "stylist" (:user-category (get @user-cache session)))))

