(ns chudao.util.random
  (:import (java.util UUID)))

(defn uuid
  []
  (str (UUID/randomUUID)))

