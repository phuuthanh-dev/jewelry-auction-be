package vn.webapp.backend.auction.dto;

public record ImageRequest(
        boolean icon,
        String data,
        Integer jewelryId
) {
}
