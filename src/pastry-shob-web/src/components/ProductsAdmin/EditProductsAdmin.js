import {useUser} from "../../UserProvider/UserProvider";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";

const EditProductsAdmin = () => {

    const user = useUser();
    const {productId} = useParams();

    const [product, setProduct] = useState({
        nameProduct: "",
        price: null,
        categories: "",
        description: "",
        image: "",
        shopName: ""
    })

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
                                // onChange={(e ) => updateProduct("nameProduct", e.target.value)}
                                value={product.nameProduct}
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