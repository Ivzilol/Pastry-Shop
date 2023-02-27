// import React, {useEffect, useState} from 'react';
// import {Button} from "react-bootstrap";
// import Comment from "../Comment/Comment";
// import ajax from "../../Services/FetchService";
// import {useUser} from "../../UserProvider/UserProvider";
// import {useInterval} from "../../util/useInterval";
// import dayjs from "dayjs";
//
// const CommentContainer = (props) => {
//     const {shopId} = props;
//     const user = useUser();
//
//     const emptyComment = {
//         id: null,
//         text: "",
//         shopId: shopId !== null ? parseInt(shopId) : null,
//         user: user.jwt,
//         createdDate: null,
//     }
//
//     const [comment, setComment] = useState(emptyComment);
//     const [comments, setComments] = useState([]);
//
//     useInterval(() => {
//         updateCommentTimeDisplay();
//     }, 1000 * 5);
//
//     function updateCommentTimeDisplay() {
//         const commentsCopy = [...comments];
//         commentsCopy.forEach(
//             (comment) => (comment.createdDate = dayjs(comment.createdDate))
//         );
//         formatComments(commentsCopy);
//     }
//
//     function handleEditComment(commentId) {
//         const index = comments.findIndex(comment => comment.id === commentId);
//         const commentCopy = {
//             id: comments[index].id,
//             text: comments[index].text,
//             shopId: shopId !== null ? parseInt(shopId) : null,
//             user: user.jwt,
//             createdDate: comments[index].createdDate,
//         }
//         setComment(commentCopy);
//     }
//
//     function handleDeleteComment(commentId) {
//         ajax(`/api/comments/${commentId}`, "DELETE", user.jwt).then((msg) => {
//             const commentsCopy = [...comments];
//             const i = commentsCopy.findIndex((comment) => comment.id === commentId);
//             commentsCopy.splice(i, 1);
//             setComments(commentsCopy);
//         });
//     }
//
//     function formatComments(commentsCopy) {
//         commentsCopy.forEach((comment) => {
//             if (typeof comment.createDate === "string") {
//                 comment.createDate = dayjs(comment.createDate);
//             }
//         });
//         setComments(commentsCopy);
//     }
//
//
//     useEffect(() => {
//         ajax(`/api/comments?shopId=${shopId}`,
//             'GET',
//             user.jwt,
//             null)
//             .then((commentsData) => {
//                 formatComments(commentsData)
//             });
//     }, []);
//
//     function updateComment(value) {
//         const commentCopy = {...comment}
//         commentCopy.text = value;
//         setComment(commentCopy);
//     }
//
//     function submitComment() {
//         if (comment.id) {
//             ajax(`/api/comments/${comment.id}`, "PUT", user.jwt, comment).then(d => {
//                 const commentsCopy = [...comments]
//                 const index = commentsCopy.findIndex(comment => comment.id === d.id);
//                 commentsCopy[index] = d;
//                 formatComments(commentsCopy);
//                 setComment(emptyComment);
//             })
//         } else {
//             ajax('/api/comments', 'POST', user.jwt, comment).then(d => {
//                 const commentsCopy = [...comments]
//                 commentsCopy.push(d);
//                 formatComments(commentsCopy);
//                 setComment(emptyComment);
//             });
//         }
//     }
//
//     return (
//         <>
//             <div className="comments">
//                         <textarea
//                             onChange={(e) => updateComment(e.target.value)}
//                             value={comment.text}
//                         >
//                         </textarea>
//                 <Button
//                     onClick={() => submitComment()}
//                 >Post Comment
//                 </Button>
//             </div>
//             <div className="comments-view">
//                 {comments.map((currentComment) => (
//                     <Comment
//                         key={currentComment.id}
//                         commentData={currentComment}
//                         emitDeleteComment={handleDeleteComment}
//                         emitEditComment={handleEditComment}
//                     />
//                 ))}
//             </div>
//         </>
//     );
// };
//
// export default CommentContainer;