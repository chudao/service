(ns chudao.service.upload
  (:require [clojure.java.io :as io]
            [io.pedestal.http :as bootstrap]
            [ring.middleware.multipart-params :as multipart-params]
            [amazonica.aws.s3 :as s3]
            [amazonica.aws.s3transfer :as s3t]))


(defn upload-photo
  [request]
  (let [mprequest (multipart-params/multipart-params-request request)
        params (:multipart-params mprequest)
        file (get params "file")
        length (:size file)
        file-name (:filename file)
        content-type (:content-file file)
        input-file (:tempfile file)]
    (bootstrap/json-response
      (s3/put-object :bucket-name "chudao-photos"
                  :key file-name
                  :metadata {:content-length length :content-type "image/jpeg"}
                  :file input-file))))

