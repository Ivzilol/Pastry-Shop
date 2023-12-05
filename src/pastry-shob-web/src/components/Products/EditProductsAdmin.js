import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import baseURL from "../BaseURL/BaseURL";

const EditProductsAdmin = () => {

    const user = useUser();
    const {productId} = useParams();
    const navigate = useNavigate();
    const [imageUrl, setImageUrl] = useState("");
    const [product, setProduct] = useState({
            name: "",
            price: null,
            categories: "",
            description: "",
        }
    );


    useEffect(() => {
        ajax(`${baseURL}api/products/${productId}`, "GET", user.jwt)
            .then(productData => {
                setProduct(productData);
            })
    }, [productId, user.jwt]);

    function saveProduct() {
        persist();
        navigate("/products");
    }

    function updateProduct(prop, value) {
        const newProduct = {...product}
        newProduct[prop] = value;
        setProduct(newProduct);
    }

    const imageSubmit = (e) => {
        if (e.target.files[0] && e.target.files[0].name !== "") {
            setImageUrl(e.target.files[0])
        }
    }


    function editProductBody() {
        const formData = new FormData();
        formData.append("imageUrl", imageUrl)
        const dto = {
            name: product.name,
            price: product.price,
            categories: product.categories,
            description: product.description
        }
        formData.append("dto",
            new Blob([JSON.stringify(dto)], {
                type: "application/json",
            })
        );
        return formData;
    }

    function persist() {
        const formData = editProductBody();
        fetch(`${baseURL}api/products/edit/${productId}`, {
            method: "PATCH",
            body: formData
        })
            .then(productData => {
                if (productData.status === 200) {
                    alert("Successful update product!")
                } else {
                    alert(productData.custom)
                }
            })
    }

    function DeleteProducts() {
        ajax(`${baseURL}api/products/${productId}`, "DELETE", user.jwt)
            .then((response) => {
                alert(response.custom)
                navigate("/products")
            });
    }


    return (
        <main>
            <section className="products-edit">
                {product ? (
                    <>
                        <article className="products-edit-container">
                            <h5 className="products-edit-item">Име на продукта</h5>
                            <input
                                onChange={(e) => updateProduct("name", e.target.value)}
                                value={product.name}
                                name="name"
                                id="name"
                                type="text"
                            />
                            <h5 className="products-edit-item">Цена</h5>
                            <input
                                onChange={(e) => updateProduct("price", e.target.value)}
                                value={product.price}
                                name="price"
                                id="price"
                                type="number"
                            />
                            <h5 className="products-edit-item">Категория</h5>
                            <input
                                onChange={(e) => updateProduct("categories", e.target.value)}
                                value={product.categories}
                                name="categories"
                                id="categories"
                                type="text"
                            />
                            <h5 className="products-edit-item">Описание</h5>
                            <input
                                onChange={(e) => updateProduct("description", e.target.value)}
                                value={product.description}
                                name="description"
                                id="description"
                                type="text"
                            />
                            <h5 className="products-edit-item">Настояща снимка</h5>
                            <img className="products-edit-item-img" src={product.imageUrl} alt="new"/>
                            <h5 className="products-edit-item">НОВА СНИМКА</h5>
                            <input
                                className="input-image"
                                type="file"
                                accept='image/png, image/jpeg'
                                size='sm'
                                id="imageUrl"
                                name="imageUrl"
                                placeholder="Image"
                                onChange={imageSubmit}
                            />
                            <div className="products-edit-buttons">
                                <div className="products-edit-button">
                                    <button
                                        type="submit"
                                        onClick={() => saveProduct()}
                                    >Редактирай
                                    </button>
                                </div>
                                <div className="products-edit-button">
                                    <button
                                        type="submit"
                                        onClick={() => navigate("/products")}>
                                        Продукти
                                    </button>
                                </div>
                                <div className="products-edit-button">
                                    <button
                                        id="submit"
                                        type="button"
                                        onClick={DeleteProducts}
                                    >
                                        Изтрий
                                    </button>
                                </div>
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