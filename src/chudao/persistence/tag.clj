(ns chudao.persistence.tag
  (:import (java.sql SQLException))
  (:require [korma.core :as korma]
            [clojure.string :as str]
            [clojure.set :as set]))

(korma/defentity Tag)
(korma/defentity ProductTag)

(defn- reverse-key-value
  [entry]
  (let [tag-name (:TagName entry)
        tag-id (:TagId entry)]
    {tag-name tag-id}))

(defn- get-all-tags
  []
  (->>
    (korma/select Tag)
    (map reverse-key-value)
    (apply merge)))

(def tags-cache (atom {}))

(defn- add-new-tags
  [tags]
  (let [values (->> tags
                    (map #(str "('" % "')"))
                    (clojure.string/join ","))
        sql-string (str "INSERT IGNORE INTO "
                        "`" (System/getenv "DB_NAME") "`"
                        ".`Tag` (`TagName`) VALUES "
                        values ";")]
    (try
      (korma/exec-raw sql-string)
      (reset! tags-cache (get-all-tags))
      (catch SQLException e
        (prn e)
        (case (.getErrorCode e)
          :genric-error)))))

(defn- get-tag-ids
  [tags]
  (let [cache-tags-set (set (keys @tags-cache))
        tags-set (set tags)]
    (do
      (if-not (set/subset? tags-set cache-tags-set)
              (add-new-tags (set/difference tags-set cache-tags-set)))
      (vals (select-keys @tags-cache tags))
      )))

(defn- build-tag-product-association
  [product-id tag-ids]
  (reduce (fn [m v]
            (conj m {:ProductId product-id, :TagId v}))
          []
          tag-ids))

(defn insert-tags
  [product-id tags]
  (let [tag-ids (get-tag-ids tags)
        values (build-tag-product-association product-id tag-ids)]
    (try
      (korma/insert ProductTag (korma/values values))
      (catch SQLException e
        (prn e)
        (case (.getErrorCode e)
          :genric-error)))))

(defn- union-query
  [tag-ids]
  (korma/select ProductTag
                (korma/modifier "DISTINCT")
                (korma/fields :ProductId)
                (korma/where (in :TagId tag-ids))))

(defn- parse-result
  [result]
  (let [ids (reduce
              (fn [m v]
                (conj m (:ProductId v)))
              []
              result)]
    {:ProductIds ids}))

(defn find-products-by-tags
  [tags logic-op]
  (->
    tags
    (str/split #",")
    get-tag-ids
    union-query
    parse-result))


