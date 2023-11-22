import React, {useEffect, useRef, useState} from 'react';
import ajax from "../../Services/FetchService";
import {useNavigate, useParams} from "react-router-dom";
import {useUser} from "../../UserProvider/UserProvider";
import Comment from "../Comment/Comment";
import NavBar from "../NavBar/NavBar";
import ShopArt from "../ShopArt/ShopArt";
import Footer from "../Footer/Footer";
import OrderWindow from "../Orders/OrderWindow";
import baseURL from "../BaseURL/BaseURL";

const ShopsView = () => {
    useNavigate();
    const user = useUser();
    const {shopId} = useParams()
    const textareaRef = useRef(null);
    const [shop, setShop] = useState({
        town: "",
        address: "",
        number: null
    });

    const prevShopValue = useRef(shop);

    const emptyComment = {
        id: null,
        text: "",
        shopId: shopId !== null ? parseInt(shopId) : null,
        user: user.jwt,
    }

    const [comment, setComment] = useState(emptyComment);
    const [comments, setComments] = useState([]);


    function handleEditComment(commentId) {
        const index = comments.findIndex(comment => comment.id === commentId);
        const commentCopy = {
            id: comments[index].id,
            text: comments[index].text,
            shopId: shopId !== null ? parseInt(shopId) : null,
            user: user.jwt,
        }
        setComment(commentCopy);
    }

    function refreshPage() {
        window.location.reload();
    }

    function handleDeleteComment(commentId) {
        ajax(`${baseURL}api/comments/user/${commentId}`, "DELETE", user.jwt)
            .then((response) => {
                if (response.custom === 'Successful delete message') {
                    refreshPage()
                }
            });
    }

    function submitComment() {
        if (comment.text === '') {
            return;
        }
        if (comment.id) {
            ajax(`${baseURL}api/comments/${comment.id}`, "PUT", user.jwt, comment)
                .then(d => {
                    const commentsCopy = [...comments]
                    const index = commentsCopy.findIndex(comment => comment.id === d.id);
                    commentsCopy[index] = d;
                    setComments(commentsCopy);
                    setComment(emptyComment);
                });
        } else {
            ajax(`${baseURL}api/comments`, 'POST', user.jwt, comment)
                .then(d => {
                    const commentsCopy = [...comments]
                    commentsCopy.push(d);
                    setComments(commentsCopy);
                    setComment(emptyComment);
                });
        }
    }

    useEffect(() => {
        ajax(`${baseURL}api/comments?shopId=${shopId}`,
            'GET',
            user.jwt,
            null)
            .then(commentsData => {
                setComments(commentsData)
            });
    }, [shopId, user.jwt])

    function updateComment(value) {
        const commentCopy = {...comment}
        commentCopy.text = value;
        setComment(commentCopy);
    }

// eslint-disable-next-line react-hooks/exhaustive-deps
    function persist() {
        ajax(`${baseURL}api/shops/${shopId}`, "PATCH", user.jwt, shop)
            .then(shopData => {
                    setShop(shopData);
                }
            );
    }

    useEffect(() => {
        persist();
        prevShopValue.current = shop;
    }, [persist, shop])

    useEffect(() => {
        ajax(`${baseURL}api/shops/${shopId}`, "GET", user.jwt)
            .then(shopResponse => {
                let shopData = shopResponse;
                if (shopData.town === null) shopData.town = ""
                if (shopData.address === null) shopData.address = ""
                setShop(shopData);
            });
    }, [shopId, user.jwt]);


    useEffect(() => {
        focusTextarea()
    })

    function focusTextarea() {
        textareaRef.current.focus();
    }


    return (
            <main className="user-comments-container">
                <NavBar/>
                <ShopArt/>
                <OrderWindow/>
                <div className="comments"

                >
                        <textarea placeholder="Напиешете Вашето мнение тук..."
                                  className="comments-textarea"
                                  ref={textareaRef}
                                  onChange={(e) => updateComment(e.target.value)}
                                  value={comment.text}
                        >
                        </textarea>
                    <button className="user-comments-container-button"
                            onClick={() => submitComment()}
                    >Post Comment
                    </button>
                </div>
                <div className="comments-view">
                    {comments.map(currentComment => (
                        <Comment
                            createdBy={currentComment.createdBy}
                            text={currentComment.text}
                            emitDeleteComment={handleDeleteComment}
                            emitEditComment={handleEditComment}
                            id={currentComment.id}
                        />
                    ))}
                </div>
                <Footer/>
            </main>
    );
};

export default ShopsView;