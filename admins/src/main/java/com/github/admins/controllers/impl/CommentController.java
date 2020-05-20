package com.github.admins.controllers.impl;

import com.github.admins.controllers.ICommentController;
import com.github.admins.dto.CommentDto;
import com.github.admins.payload.Comment;
import com.github.admins.payload.Product;
import com.github.admins.services.ICommentService;
import com.github.admins.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.admins.utils.TransferObj.fromComment;
import static com.github.admins.utils.TransferObj.toComment;

@RestController
@RequestMapping(path = "/v1/comment")
@RequiredArgsConstructor
public class CommentController implements ICommentController {

    private final ICommentService commentService;

    private final IProductService productService;

    @Override
    public CommentDto
    addComment(Long productId, CommentDto payload) {
        Comment tc = toComment(payload);
        Product product = this.productService.readById(productId);
        Comment comment = this.commentService.create(tc);
        product.addComment(comment);
        this.productService.update(product);
        return fromComment(comment);
    }

    @Override
    public CommentDto getComment(Long id) {
        var comment = this.commentService.readById(id);
        return fromComment(comment);
    }

    @Override
    public void remove(Long id) {
        this.commentService.remove(id);
    }
}
