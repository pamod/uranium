package org.uranium.qna.processors;

import org.springframework.stereotype.Component;


/**
 * Process answer for a question
 */
public interface Processor {
    /**
     * <p>
     * Given engine should accept incoming question as a text, return the processed answer as text.
     * </p>
     *
     * @param question question in text
     * @return answer in plain text, answer will be "ERROR" if there was a failure.
     */
    String process(String question);
}
