(ns chudao.service.upload
  (:require [clojure.java.io :as io]
            [io.pedestal.http :as bootstrap]
            [ring.middleware.multipart-params :as multipart-params]
            [chudao.service.data :as data]
            [amazonica.aws.s3 :as s3]
            [amazonica.aws.s3transfer :as s3t]))

(defn- uuid [] (str (java.util.UUID/randomUUID)))

(defn upload-photo
  [request]
  (let [mprequest (multipart-params/multipart-params-request request)
        params (:multipart-params mprequest)
        file (get params "file")]
      (s3/put-object :bucket-name "chudao-photos"
                     :key (str (uuid) "---" (:filename file))
                     :metadata {:content-length (:size file)
                                :content-type (:content-type file)}
                     :file (:tempfile file)))
  (bootstrap/json-response data/upload-success))



