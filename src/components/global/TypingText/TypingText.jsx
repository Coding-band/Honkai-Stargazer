import React, { Component } from "react";
import { Text, View } from "react-native";
import PropTypes from "prop-types";

export default class TypingText extends Component {
  constructor() {
    super();

    this.index = 0;

    this.typing_timer = -1;

    this.blinking_cursor_timer = -1;

    this.state = { text: "", blinking_cursor_color: "transparent" };
  }

  componentDidMount() {
    this.typingAnimation();
    this.blinkingCursorAnimation();
  }

  componentWillUnmout() {
    clearTimeout(this.typing_timer);

    this.typing_timer = -1;

    clearInterval(this.blinking_cursor_timer);

    this.blinking_cursor_timer = -1;
  }

  typingAnimation = () => {
    clearTimeout(this.typing_timer);

    this.typing_timer = -1;

    if (this.index < this.props.text.length) {
      if (this.refs.animatedText) {
        this.setState(
          { text: this.state.text + this.props.text.charAt(this.index) },
          () => {
            this.index++;

            this.typing_timer = setTimeout(() => {
              this.typingAnimation();
            }, this.props.typingAnimationDuration);
          }
        );
      }
    }
  };

  blinkingCursorAnimation = () => {
    this.blinking_cursor_timer = setInterval(() => {
      if (this.refs.animatedText) {
        if (this.state.blinking_cursor_color == "transparent")
          this.setState({ blinking_cursor_color: this.props.color });
        else this.setState({ blinking_cursor_color: "transparent" });
      }
    }, this.props.blinkingCursorAnimationDuration);
  };

  render() {
    return (
      <View>
        <Text
          ref="animatedText"
          style={{
            color: this.props.color,
            fontSize: this.props.textSize,
            fontFamily: this.props.fontFamily,
            textAlign: "center",

          }}
        >
          {this.state.text}
          {this.props.isBlinking && <Text style={{ color: this.state.blinking_cursor_color }}>|</Text>}
        </Text>
      </View>
    );
  }
}

TypingText.propTypes = {
  text: PropTypes.string,
  color: PropTypes.string,
  textSize: PropTypes.number,
  fontFamily: PropTypes.string,
  typingAnimationDuration: PropTypes.number,
  blinkingCursorAnimationDuration: PropTypes.number,
  isBlinking: PropTypes.bool
};

TypingText.defaultProps = {
  text: "Default Typing Animated Text.",
  color: "rgb( 77, 192, 103 )",
  textSize: 30,
  fontFamily: "",
  typingAnimationDuration: 50,
  blinkingCursorAnimationDuration: 190,
  isBlinking: false
};
