package org.job.interview.roombookingservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageableResponse<T> {
    List<T> content;
    Integer number;
    Integer size;
    Long total;
    Long totalElements;

    public PageableResponse(List<T> content, Pageable pageable, long total) {
        this.content = content;
        this.number = pageable.getPageNumber();
        this.size = pageable.getPageSize();
        this.total = pageable.toOptional().filter(it -> !content.isEmpty())//
                .filter(it -> it.getOffset() + it.getPageSize() > total)//
                .map(it -> it.getOffset() + content.size())//
                .orElse(total);
        this.totalElements = total;
    }

    public PageableResponse(List<T> content) {
        this.content = content;
    }

    public Integer getTotalPages() {
        return getSize() == null ? null : (int) Math.ceil((double) total / (double) getSize());
    }

    private Boolean hasNext() {
        if(getNumber() == null) {
            return null;
        }
        if(getNumber() == 0) {
            if(getTotal() <= getSize()) {
                return false;
            }
            return getNumber() + 1 < getTotal() - 1;
        }
        if(getTotalPages() - getNumber() == 1 && getTotal() % getSize() != 0){
            return false;
        }
        return getNumber() + 1 < getTotal();
    }

    public Boolean isLast() {
        return hasNext() == null ? null : !hasNext();
    }

    public Boolean isFirst(){
        return getNumber() == null ? null : getNumber() == 0;
    }

    public Boolean isEmpty(){
        return content.isEmpty();
    }
}