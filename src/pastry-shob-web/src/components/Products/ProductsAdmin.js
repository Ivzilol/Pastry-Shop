import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";
import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";
import baseURL from "../BaseURL/BaseURL";

const ProductsAdmin = () => {
    const user = useUser()
    const [products, setProducts] = useState(null);
    let navigate = useNavigate();

    useEffect(() => {
        ajax(`${baseURL}api/products`, "GET", user.jwt)
            .then(productsData => {
                setProducts(productsData);
            });

        if (!user.jwt) navigate("/login")
    }, [navigate, user.jwt]);

    return (
        <section className="products-all">
            <NavBarAdmin/>
            {products ? (
                <article className="products-container">
                    <button
                        className="products-container-createButton"
                        onClick={() => {
                            window.location.href = `/products/create`
                        }}
                    >Създай нов продукт
                    </button>
                    {products.map((product) => (
                        <div
                            className="products-container-items"
                            key={product.id}
                        >
                            <p className="products-container-item">Име на продукта: {product.name}</p>
                            <p className="products-container-item">Цена на продукта: {product.price}</p>
                            <p className="products-container-item">Категория: {product.categories}</p>
                            <p className="products-container-item">Описание: {product.description}</p>
                            <img className="product-img" src={product.imageUrl} alt="new"/>
                            {/*<p className="products-container-item">Product shopName: {product.shops.name}</p>*/}
                            <div className="products-container-item-button">
                                <button
                                    id="submit"
                                    type="button"
                                    onClick={() => {
                                        window.location.href = `/products/${product.id}`
                                    }}
                                >
                                    Редакция
                                </button>
                            </div>
                        </div>
                    ))}
                </article>
            ) : (
                <></>
            )}
        </section>
    )
}

export default ProductsAdmin;