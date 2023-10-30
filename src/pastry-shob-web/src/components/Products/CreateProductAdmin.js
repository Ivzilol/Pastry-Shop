import {useUser} from "../../UserProvider/UserProvider";
import {useState} from "react";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";
import {useNavigate} from "react-router-dom";
import baseURL from "../BaseURL/BaseURL";

const CreateProductAdmin = () => {
    useUser();
    let navigate = useNavigate();
    const [name, setName] = useState("");
    const [price, setPrice] = useState(0);
    const [categories, setCategories] = useState("");
    const [description, setDescription] = useState("");
    const [imageUrl, setImageUrl] = useState("");
    const [shopName, setShopName] = useState("");

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
            .then((response) => {
                if (response.status === 200)
                    return Promise.all([response.json(), response.headers]);
                else return Promise.reject("Invalid product");
            })
            .catch((message) => {
                alert(message)
            })
            .then(() => {
                navigate("/products")
            });
    }


    return (
        <>
            <NavBarAdmin/>
            <section className="products">
                <article className="products-form">
                    <h1>Add Product</h1>
                    <label form="name">Name Product</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        placeholder="Name Product"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                    <label
                        htmlFor="price"
                    > Price
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
                    <label form="categories">Categories</label>
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
                    <label className="description">Description</label>
                    <input
                        type="text"
                        id="description"
                        name="description"
                        placeholder="Description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />
                    <label className="imageUrl">Image</label>
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
                    <label className="shopName">Shop Name</label>
                    <input
                        type="text"
                        id="ShopId"
                        name="ShopId"
                        placeholder="Shop Name"
                        value={shopName}
                        onChange={(e) => setShopName(e.target.value)}
                    />
                    <button
                        id="submit-product"
                        type="button"
                        onClick={() => createProduct()}
                    >
                        Create Product
                    </button>
                </article>
            </section>
        </>
    );
}

export default CreateProductAdmin;
