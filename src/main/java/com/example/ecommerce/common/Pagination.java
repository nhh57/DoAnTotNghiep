package com.example.ecommerce.common;

/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */




import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A pagination object to be used to describe pagination (offset, limit) which can be used in queries.
 *
 * @author <a href="mailto:nscavell@redhat.com">Nick Scavelli</a>
 */
public class Pagination implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int offset;
    private final int limit;
    private final int page;

    public Pagination(int page, int limit) {
        if (limit <= 0)
            limit = Constants.DEFAULT_NUMBER_RESULT_IN_PAGE;
        if (limit > Constants.MAXIMUM_NUMBER_RESULT_IN_PAGE)
            limit = Constants.MAXIMUM_NUMBER_RESULT_IN_PAGE;

        this.page = page;
        this.limit = limit;
    }

    /**
     * The current page number this pagination object represents
     *
     * @return the page number
     */
    public int getPageNumber() {
        if (offset < limit || limit == 0)
            return 1;

        return (offset / limit) + 1;
    }

    /**
     * The offset for this pagination object. The offset determines what index (0 index) to start retrieving results from.
     *
     * @return the offset
     */
    public int getOffset() {
        if(page < 1)
            return 0;
        return (page - 1) * limit;
    }

    /**
     * The limit for this pagination object. The limit determines the maximum amount of results to return.
     *
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Creates a new pagination object representing the next page
     *
     * @return new pagination object with offset shifted by offset+limit
     */
    public Pagination getNext() {
        return new Pagination(offset + limit, limit);
    }

    /**
     * Creates a new pagination object representing the previous page
     *
     * @return new pagination object with offset shifted by offset-limit
     */
    public Pagination getPrevious() {
        if (limit >= offset) {
            return new Pagination(0, limit);
        } else {
            return new Pagination(offset - limit, limit);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Pagination))
            return false;

        Pagination pagination = (Pagination) o;

        return (limit == pagination.limit) && (offset == pagination.offset);
    }

    @Override
    public int hashCode() {
        int result = offset;
        result = 31 * result + limit;
        return result;
    }
    public static List<Integer> getListPage(int page, int size){
        List<Integer> list=new ArrayList<>();
        if(size <=11){
            for(int i=1;i<=size;i++){
                list.add(i);
            }
        }else{
            if(page <=6){
                for(int i=1;i<=11;i++){
                    if(i<=8){
                        list.add(i);
                    }else if(i==9){
                        list.add(0);
                    }else if(i==10){
                        list.add(size-1);
                    }else if(i==11){
                        list.add(size);
                    }
                }
            }else{
                if(page + 5 < size){
                    for(int i=1;i<=11;i++){
                        if(i<=2){
                            list.add(i);
                        }else if(i==3){
                            list.add(0);
                        }else if(i==4){
                            for(int j=page-2;j<=page+2;j++){
                                list.add(j);
                            }
                        }else if(i==9){
                            list.add(0);
                        }else if(i==10){
                            list.add(size-1);
                        }else if(i==11){
                            list.add(size);
                        }
                    }
                }else{
                    for(int i=1;i<=11;i++){
                        if(i<=2){
                            list.add(i);
                        }else if(i==3){
                            list.add(0);
                        }else if(i==4){
                            for(int k=page-2;k<=size;k++){
                                list.add(k);
                            }
                        }
                    }
                }
            }
        }
        return list;
    }
}

