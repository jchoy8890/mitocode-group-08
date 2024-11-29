db.createUser(
        {
            user: "jchoy",
            pwd: "jchoy",
            roles: [
                {
                    role: "readWrite",
                    db: "mitocode"
                }
            ]
        }
);
db.createCollection('collection_user');
db.createCollection('collection_product');