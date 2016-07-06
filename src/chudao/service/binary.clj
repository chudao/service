(ns chudao.service.binary
  (:require [clojure.java.io :as io]
            [io.pedestal.http :as bootstrap]
            [ring.util.response :as ring-resp]
            [ring.middleware.multipart-params :as multipart-params]
            [chudao.service.data :as data]
            [chudao.persistence.binary :as persist-binary]))

(defn upload-file
  [request]
  (let [mprequest (multipart-params/multipart-params-request request)
        params (:multipart-params mprequest)
        result (persist-binary/upload-file params)]
    (prn "############### Printing original request")
    (prn request)
    (prn "############### Done Printing original request")
    (prn "############### Printing multipart request")
    (prn mprequest)
    (prn "############### Done Printing multipart request")
    (bootstrap/json-response
      (cond
        (map? result) data/upload-success
        (= result :user-id-not-exists) data/upload-failure-user-id-not-exists
        (= result :user-id-invalid) data/upload-failure-user-id-invalid
        )
      )))

(defn download-file
  [request]
  (let [file-name (get-in request [:json-params :file-name])
        data (persist-binary/download-file file-name)]
    (-> (ring-resp/response (:file data))
        (ring-resp/content-type (:content-type data))
        (ring-resp/header "Content-Disposition" (str "attachment; filename=" file-name)))))

(defn download-file-get
  [request]
  (let [file-name (get-in request [:query-params :file-name])
        data (persist-binary/download-file file-name)]
    (-> (ring-resp/response (:file data))
        (ring-resp/content-type (:content-type data))
        (ring-resp/header "Content-Disposition" (str "attachment; filename=" file-name)))))

