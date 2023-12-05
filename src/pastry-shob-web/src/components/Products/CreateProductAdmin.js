import {useUser} from "../../UserProvider/UserProvider";
import React, {useState} from "react";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";
import {useNavigate} from "react-router-dom";
import baseURL from "../BaseURL/BaseURL";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faInfoCircle} from "@fortawesome/free-solid-svg-icons";

const CreateProductAdmin = () => {
    useUser();
    let navigate = useNavigate();
    const [name, setName] = useState("");
    const [price, setPrice] = useState(0);
    const [categories, setCategories] = useState("");
    const [description, setDescription] = useState("");
    const [imageUrl, setImageUrl] = useState("");
    const [shopName, setShopName] = useState("");
    const [errorCreateProduct, setErrorCreateProduct] = useState(false);

    const imageSubmit = (e) => {
        if (e.target.files[0] && e.target.files[0].name !== "") {
            setImageUrl(e.target.files[0])
        }
    }

    function createProduct() {
        const formData = new FormData();
        formData.append("imageUrl", imageUrl)
        const dto = {
            name: name,
            price: price,
            categories: categories,
            description: description,
            shopName: shopName,
        }

        formData.append("dto",
            new Blob([JSON.stringify(dto)], {
                type: "application/json",
            })
        );

        fetch(`${baseURL}api/products/create/admin`, {
            method: "POST",
            body: formData,
        })
            .then((response) => response.json())
            .then(data => {
                if (data.custom !== "Successful create product") {
                    setErrorProduct(data)
                    setErrorCreateProduct(true)
                } else {
                    alert("Successful create product")
                    navigate('/products')
                }
            });
    }

    const [errorProduct, setErrorProduct] = useState({
        nameError: '',
        priceError: '',
        categoriesError: '',
        descriptionError: '',
        shopError: ''
    })


    return (
        <>
            <NavBarAdmin/>
            <section className="products">
                <article className="products-form">
                    <h1>Add Product</h1>
                    <label form="name">Име на продукта</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        placeholder="Name Product"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />

                    {errorCreateProduct && errorProduct.nameError !== null &&
                        <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {errorProduct.nameError}</span>
                    }

                    <label
                        htmlFor="price"
                    > Цена
                    </label>
                    <input
                        type="number"
                        id="price"
                        autoComplete="off"
                        name="price"
                        placeholder="Price"
                        value={price}
                        onChange={(e) => setPrice(e.target.valueAsNumber)}
                    />

                    {errorCreateProduct && errorProduct.priceError !== null &&
                        <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {errorProduct.priceError}</span>
                    }

                    <label form="categories">Категория</label>
                    <select
                        id="categories"
                        name="categories"
                        placeholder="Categories"
                        value={categories}
                        onChange={(e) => setCategories(e.target.value)}
                    >
                        <option value="">Select Categories</option>
                        <option value="pie">pie</option>
                        <option value="sweets">sweets</option>
                        <option value="buns">buns</option>
                        <option value="cake">cake</option>
                    </select>

                    {errorCreateProduct && errorProduct.categoriesError !== null &&
                        <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {errorProduct.categoriesError}</span>
                    }

                    <label className="description">Описание</label>
                    <input
                        type="text"
                        id="description"
                        name="description"
                        placeholder="Description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />

                    {errorCreateProduct && errorProduct.descriptionError !== null &&
                        <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {errorProduct.descriptionError}</span>
                    }

                    <label className="imageUrl">Снимка</label>
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
                    <label className="shopName">Име на магазина</label>
                    <input
                        type="text"
                        id="ShopId"
                        name="ShopId"
                        placeholder="Shop Name"
                        value={shopName}
                        onChange={(e) => setShopName(e.target.value)}
                    />

                    {errorCreateProduct && errorProduct.shopError !== null &&
                        <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {errorProduct.shopError}</span>
                    }

                    <button
                        id="submit-product"
                        type="button"
                        onClick={() => createProduct()}
                    >
                        Създай продукт
                    </button>
                </article>
            </section>
        </>
    );
}

export default CreateProductAdmin;
