# Frontend/Dockerfile

FROM python:3.10.8

RUN mkdir -p app/frontend

COPY Streamlit_Frontend app/frontend

WORKDIR /app/frontend

RUN pip install --upgrade pip
RUN pip install -r requirements.txt

EXPOSE 8501
# EXPOSE 8080 

ENTRYPOINT ["streamlit","run"]
# ENTRYPOINT ["uvicorn", "api_app:app", "--host", "0.0.0.0", "--port", "8080", "--reload"]

CMD ["Hello.py"]
