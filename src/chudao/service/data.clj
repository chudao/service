(ns chudao.service.data)

(defn login-success
  [result]
  {:response-code "000"
   :response-message "login success"
   :user-id (:UserId result)
   :user-name (:UserName result)
   :auth-token "womenchudaola"})

(def login-failure
  {:response-code "001"
   :response-message "login failure: user not exists or password incorrect"})

(def register-failure-duplicate
  {:response-code "011"
   :response-message "registration failure: user already exists"})

(defn register-success
  [result]
  {:response-code "010"
   :response-message "registration success"
   :user-id result
   :auth-token "womenchudaola"})

(defn upload-success
  [result]
  {:response-code "020"
   :response-message "upload success"
   :file-key result})

(def upload-failure-user-id-not-exists
  {:response-code "021"
   :response-message "user/product id not exists"})

(def upload-failure-user-id-invalid
  {:response-code "022"
   :response-message "user/product id invalid, must be number"})

(defn product-add-success
  [result]
  {:response-code "030"
   :response-message "product addition success"
   :product-id result})

(defn query-success
  [result]
  {:response-code "040"
   :response-message "query success"
   :response-data result})

(defn request-add-success
  [result]
  {:response-code "050"
   :response-message "request addition success"
   :product-id (->
                 result
                 :_id
                 .toString)})
