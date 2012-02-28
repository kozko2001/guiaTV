//
//  ProgramaModel.h
//  GuiaTv
//
//  Created by Jordi Coscolla on 24/02/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ProgramaModel : NSObject

@property  (nonatomic, strong) NSString  *title;
@property  (nonatomic, strong) NSString  *desc;
@property  (nonatomic, strong) NSString  *categoria;
@property  (nonatomic, assign) double start;
@property  (nonatomic, assign) double end;
@property  (nonatomic, strong) NSString *imagen;
@property  (nonatomic, strong) NSString  *fecha;
- (ProgramaModel*) initProgramaWithTitle: (NSString *)t AndDesc: (NSString*) d AndCategoria:(NSString*) cat AndStart: (double) _start AndEnd: (double) _end AndImage: (NSString*) imagen;

@end
