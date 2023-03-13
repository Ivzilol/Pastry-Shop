import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";
import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";

const ProductsAdmin = () => {
    const user = useUser()
    const [products, setProducts] = useState(null);
    let navigate = useNavigate();

    useEffect(() => {
        ajax("api/products", "GET", user.jwt)
            .then(productsData => {
                setProducts(productsData);
                console.log(productsData);
            });

        if (!user.jwt) navigate("/login")
    }, [user.jwt]);


    return (
        <section className="products-all">
             <NavBarAdmin/>
            {products ? (
                <article className="products-container">
                    {products.map((product) => (
                        <div
                        className="products-container-items"
                        key={product.id}
                        >
                            <p className="products-container-item">Product name: {product.name}</p>
                            <p className="products-container-item">Product price: {product.price}</p>
                            <p className="products-container-item">Product categories: {product.categories}</p>
                            <p className="products-container-item">Product description: {product.description}</p>
                            <img className="product-img" src={product.imageUrl} alt="new" />
                            <p className="products-container-item">Product shopName: {product.shops.name}</p>
                            <button
                                id="submit"
                                type="button"
                                onClick={() => {
                                    window.location.href = `/products/${product.id}`
                                }}
                            >
                                Edit
                            </button>
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