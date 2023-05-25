import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useNavigate} from "react-router-dom";
import NavBar from "../NavBar/NavBar";
import Footer from "../Footer/Footer";

const ProductPies = () => {
    const user = useUser()
    const [pies, setPies] = useState(null);
    let navigate = useNavigate();
    const baseUrl = "http://localhost:8080/";
    useEffect(() => {
        ajax(`${baseUrl}api/products/pies`, "GET", user.jwt)
            .then(piesData => {
                setPies(piesData)
            })
        if (!user.jwt) navigate("/login")
    }, [user.jwt])


    return (
        <main className="products-users">
            <NavBar/>
            <section className="pies-container">
                {pies ? (
                    <article className="products-container">
                        {pies.map((product) => (
                            <div
                                className="products-container-items"
                                key={product.id}
                                id={product.id}
                            >
                                <p className="products-container-item"
                                >Продукт: {product.name}</p>
                                <p className="products-container-item"
                                >Цена: {product.price} лв.</p>
                                <p className="products-container-item"
                                >Описание на продукта: {product.description}</p>
                                <img
                                    className="product-img" src={product.imageUrl} alt="new"
                                />
                                {/*<div className="products-container-item-likes-container">*/}
                                {/*    <p className="products-container-item-likes">*/}
                                {/*        <FontAwesomeIcon icon={faThumbsUp}*/}
                                {/*                         className="products-container-item-likes-icon"*/}
                                {/*        />*/}
                                {/*        {Number(product.userLikes.length)}</p>*/}
                                {/*    <div className="products-container-item-likes-container-buttons">*/}
                                {/*        <button*/}
                                {/*            className="products-container-item-likes-button"*/}
                                {/*            id="submit"*/}
                                {/*            type="button"*/}
                                {/*            onClick={() => likeProduct(product.id)}*/}
                                {/*        >Харесва ми*/}
                                {/*        </button>*/}

                                {/*        <button*/}
                                {/*            className="products-container-item-likes-container-button2"*/}
                                {/*            id="submit"*/}
                                {/*            type="button"*/}
                                {/*            onClick={() => {*/}
                                {/*                orderProduct(product.id);*/}
                                {/*                orderProducts(product.id);*/}
                                {/*            }}*/}
                                {/*        >*/}
                                {/*            Поръчай*/}
                                {/*        </button>*/}
                                {/*    </div>*/}
                                {/*</div>*/}
                            </div>
                        ))}
                    </article>
                ) : (
                    <></>
                )}
            </section>
            <Footer/>
        </main>
    )
}

export default ProductPies;