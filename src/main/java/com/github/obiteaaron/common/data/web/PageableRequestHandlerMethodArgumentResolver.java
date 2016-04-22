/*
 * Copyright 2013-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.obiteaaron.common.data.web;

import com.github.obiteaaron.common.data.PageableRequest;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Extracts paging information from web requests and thus allows injecting {@link PageableRequest} instances into controller
 * methods. Request properties to be parsed can be configured. Default configuration uses request parameters beginning
 * with {@link #DEFAULT_PAGE_PARAMETER}{@link #DEFAULT_QUALIFIER_DELIMITER}.
 *
 * @author Oliver Gierke
 * @author Nick Williams
 * @since 1.6
 */
public class PageableRequestHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return PageableRequest.class.equals(parameter.getParameterType());
    }

    @Override
    public PageableRequest resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
        return new PageableRequest(pageable);
    }
}
