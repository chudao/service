(ns chudao.persistence.transformer)

(defonce mappings
  {:UserId :user-id
   :UserName :user-name
   :UserCategory :user-category
   :ProductId :product-id
   :ProductName :product-name
   :ProductBrand :product-brand
   :ProductDescription :product-description
   :ProductLink :product-link
   :BrandLink :brand-link
   })

(defonce reverse-mappings
         (clojure.set/map-invert mappings))

(defn transform-sql->clj
  [original]
  (reduce (fn [m v]
            (if (contains? mappings v)
              (assoc m (get mappings v) (get original v))
              m))
          {}
          (keys original)))

(defn transform-clj->sql
  [original]
  (reduce (fn [m v]
            (if (contains? reverse-mappings v)
              (assoc m (get reverse-mappings v) (get original v))
              m))
          {}
          (keys original)))
