(ns chudao.persistence)

(def user-map (atom {}))

(defn login
  [username password]
  (prn @user-map)
  (and
    (contains? @user-map username)
    (= (get @user-map username) password)))

(defn register
  [username password]
  (prn @user-map)
  (if (contains? @user-map username)
    false
    (swap! user-map assoc username password)))
