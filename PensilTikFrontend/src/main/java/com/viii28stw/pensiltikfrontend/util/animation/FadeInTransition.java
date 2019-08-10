package com.viii28stw.pensiltikfrontend.util.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Animate a fade in effect on a node
 * 
 * Port of FadeIn from Animate.css http://daneden.me/animate by Dan Eden
 * 
 * {@literal @}keyframes fadeIn {
 * 	0% {opacity: 0;}	
 * 	100% {opacity: 1;}
 * }
 * 
 * @author Jasper Potts
 */
@SuppressWarnings("deprecation")
public class FadeInTransition extends configAnimasi {
    /**
     * Create new FadeInTransition
     * 
     * @param node The node to affect
     */
    public FadeInTransition(final Node node) {
        super(node, getTimeline(node));
        setCycleDuration(Duration.seconds(2));
        setDelay(Duration.seconds(0.2));
    }
    
    private static Timeline getTimeline(Node node) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(0),    new KeyValue(node.opacityProperty(), 0, WEB_EASE)),
                    new KeyFrame(Duration.millis(1000),  new KeyValue(node.opacityProperty(), 1, WEB_EASE)));
        return timeline;
    }
    
}
