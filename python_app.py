import cv2
from flask import Flask, jsonify
from fer import FER
import threading

app = Flask(__name__)

detector = FER(mtcnn=True)

face_cascade = cv2.CascadeClassifier(
    cv2.data.haarcascades + 'haarcascade_frontalface_default.xml'
)

current_emotion = "neutral"

def detect_emotion():
    global current_emotion

    cap = cv2.VideoCapture(0)

    while True:
        ret, frame = cap.read()

        if not ret or frame is None:
            continue

        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

        # detect face
        faces = face_cascade.detectMultiScale(gray, 1.3, 4)

        # ❌ no face → keep previous emotion
        if len(faces) == 0:
            current_emotion = "neutral"
            continue

        # ✅ detect emotion only if face present
        results = detector.detect_emotions(frame)

        if results:
            emotions = results[0]["emotions"]
            emotion, confidence = max(emotions.items(), key=lambda x: x[1])

            if confidence > 0.5:
                current_emotion = emotion
            else:
                current_emotion = "neutral"
        else:
            current_emotion = "neutral"

        print("Emotion:", current_emotion)

        cv2.putText(frame, current_emotion, (50, 50),
                    cv2.FONT_HERSHEY_SIMPLEX, 1, (0,255,0), 2)

        cv2.imshow("Emotion Detection", frame)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    cap.release()
    cv2.destroyAllWindows()


@app.route('/emotion')
def get_emotion():
    return jsonify({"emotion": current_emotion})


if __name__ == "__main__":
    threading.Thread(target=detect_emotion).start()
    app.run(port=5000)