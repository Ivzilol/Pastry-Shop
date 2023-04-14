import NavBar from "../NavBar/NavBar";
import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";

const ProductsUser = () => {
    const user = useUser()
    const [products, setProducts] = useState(null);
    let navigate = useNavigate();
    const [product, setProduct] = useState({
            name: "",
            price: null,
            categories: "",
            description: "",
            imageUrl: ""
        }
    );

    useEffect(() => {
        ajax("api/products", "GET", user.jwt)
            .then(productsData => {
                setProducts(productsData);
            });

        if (!user.jwt) navigate("/login")
    }, [user.jwt]);


    function orderProduct(id) {
        ajax(`/api/products/${id}`, "GET", user.jwt)
            .then(productData => {
                setProduct(productData);
            });

    }
    function orderProducts(id) {
        ajax(`/api/orders/${id}`, "POST", user.jwt, product)
            .then(productData => {
                setProduct(productData)
            })
    }

    return (
        <main className="products-users">
            <NavBar/>
            {products ? (
                <article className="products-container">
                    {products.map((product) => (
                        <div
                            className="products-container-items"
                            key={product.id}
                        >
                            <p className="products-container-item-title"
                            >Product shopName: {product.shops.name}</p>
                            <p className="products-container-item"
                            >Product name: {product.name}</p>
                            <p className="products-container-item"
                            >Product price: {product.price}</p>
                            <p className="products-container-item"
                            >Product description: {product.description}</p>
                            <img
                                className="product-img" src={product.imageUrl} alt="new"
                            />

                            <div className="products-container-item-button">
                                <div>
                                    <button
                                        id="submit"
                                        type="button"
                                        onClick={() => {
                                            orderProduct(product.id);
                                            orderProducts(product.id);
                                        }}
                                    >
                                        Order Product
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))}
                </article>
            ) : (
                <></>
            )}
        </main>
    )
}
export default ProductsUser;