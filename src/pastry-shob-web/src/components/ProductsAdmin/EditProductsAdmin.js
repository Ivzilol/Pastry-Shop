import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";

const EditProductsAdmin = () => {

    const user = useUser();
    const {productId} = useParams();
    const navigate = useNavigate();
    const [product, setProduct] = useState({
            name: "",
            price: null,
            categories: "",
            description: "",
            imageUrl: ""
        }
    );


    useEffect(() => {
        ajax(`/api/products/${productId}`, "GET", user.jwt)
            .then(productData => {
                setProduct(productData);
            })
    }, []);

    function saveProduct() {
        persist();
        navigate("/products");
    }

    function updateProduct(prop, value) {
        const newProduct = {...product}
        newProduct[prop] = value;
        setProduct(newProduct);
    }

    function persist() {
        ajax(`/api/products/${productId}`, "PUT", user.jwt, product)
            .then(productData => {
                setProduct(productData);
                console.log(productData);
            })
    }

    function DeleteProducts() {
        ajax(`/api/products/${productId}`, "DELETE", user.jwt)
            .then(() => {
                navigate("/products")
            });
    }


    return (
        <main>
            <section className="products-edit">
                {product ? (
                    <>
                        <article className="products-edit-container">
                            <h5 className="products-edit-item">Name Product</h5>
                            <input
                                onChange={(e) => updateProduct("name", e.target.value)}
                                defaultValue={product.name}
                                name="name"
                                id="name"
                                type="text"
                            />
                            <h5 className="products-edit-item">Price</h5>
                            <input
                                onChange={(e) => updateProduct("price", e.target.value)}
                                defaultValue={product.price}
                                name="price"
                                id="price"
                                type="number"
                            />
                            <h5 className="products-edit-item">Categories</h5>
                            <input
                                onChange={(e) => updateProduct("categories", e.target.value)}
                                defaultValue={product.categories}
                                name="categories"
                                id="categories"
                                type="text"
                            />
                            <h5 className="products-edit-item">Description</h5>
                            <input
                                onChange={(e) => updateProduct("description", e.target.value)}
                                defaultValue={product.description}
                                name="description"
                                id="description"
                                type="text"
                            />
                            <h5 className="products-edit-item">URL</h5>
                            <input
                                onChange={(e) => updateProduct("imageUrl", e.target.value)}
                                defaultValue={product.imageUrl}
                                name="imageUrl"
                                id="imageUrl"
                                type="text"
                            />
                            <div className="products-edit-button">
                                <button
                                    type="submit"
                                    onClick={() => saveProduct()

                                }
                                >Edit Shop
                                </button>
                            </div>
                            <div className="products-edit-button">
                                <button
                                    type="submit"
                                    onClick={() => navigate("/products")}>Products
                                </button>
                            </div>
                            <div className="products-container-item-button">
                                <button
                                    id="submit"
                                    type="button"
                                    onClick={DeleteProducts}
                                >
                                    Delete
                                </button>
                            </div>
                        </article>
                    </>
                ) : (
                    <></>
                )}
            </section>
        </main>
    );
}

export default EditProductsAdmin;