{ 
 Log(1)=0, 
 Log(E)=1, 
 Log(E^(x_IntegerQ)):=x, 
 Log(E^(x_Rational)):=x, 
 Log(E^(I))=I, 
 Log(E^(-I))=(-I),
 Log(0)=(-Infinity),
 

 Log(a_,E):=Log(a)^(-1),
 Log(a_,E^m_IntegerQ):=m*Log(a)^(-1),
 Log(a_,0):=(-Infinity)*Log(a)^(-1), 
 Log(a_,1)=0, 
 Log(a_,-1):=I*Pi*Log(a)^(-1),
 Log(E,z_):=Log(z),
 Log(0,z_)=0,
 Log(1,z_)=ComplexInfinity,
 Log(-1,z_):=(-I/Pi)*Log(z),
 Log(a_,1)=Indeterminate
 
}