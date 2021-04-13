from tn.dari.spring.serivce import DeliveryManService

import random

class DeliveryManServicePython(DeliveryManService):
    def __init__(self):
        livreur = ['Younes Nahali : 98653214', 'Salim Ben Younes : 23568974', 'Mouhamed Snoussi : 55471236', 'Saif Gasmi : 93652147', 'Aziz Chtourou : 54129873']
        self.value=random.choice(livreur)
       
    def getLivreur(self):
        return self.value
        
        