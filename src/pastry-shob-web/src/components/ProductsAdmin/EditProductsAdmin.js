import {useUser} from "../../UserProvider/UserProvider";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";

const EditProductsAdmin = () => {

    const user = useUser();
    const {productId} = useParams();

    const [product, setProduct] = useState(null)

    useEffect(() => {
        ajax(`/api/products/${productId}`, "GET", user.jwt)
            .then(productResponse => {
                setProduct(productResponse);
            })
    }, []);

    function updateProduct(prop, value) {

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