package com.franzruiz.fipere1.Model;


    public class TipoGasto {

        private String text;
        private int value;


        public TipoGasto(String text, int value){
            this.text = text;
            this.value = value;
        }

        public void setText(String text){
            this.text = text;
        }

        public String getText(){
            return this.text;
        }

        public void setValue(int value){
            this.value = value;
        }

        public int getValue(){
            return this.value;
        }
    }

