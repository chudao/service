(ns chudao.service.upload
  (:require [clojure.java.io :as io]
            [io.pedestal.http :as bootstrap]
            [amazonica.aws.s3 :as s3]
            [amazonica.aws.s3transfer :as s3t]))


(defn upload-photo
  [request]
  (let [stream (:body request)
        length (:content-length request)]
    (bootstrap/json-response
      (s3/put-object :bucket-name "chudao-photos"
                  :key "sexy-girl"
                  :metadata {:content-length length}
                  :input-stream stream))))

