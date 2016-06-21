(ns chudao.upload
  (:require [clojure.java.io :as io]
            [io.pedestal.http :as bootstrap]
            [amazonica.aws.s3 :as s3]
            [amazonica.aws.s3transfer :as s3t]))


(defn upload-photo
  [request]
  (let [file (io/file (io/resource "MasturiCon2015.jpg"))]
    (bootstrap/json-response
      (s3/put-object :bucket-name "chudao-photos"
                  :key "foo"
                  :file file))))

